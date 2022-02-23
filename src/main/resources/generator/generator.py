import csv
import json
import random
from PIL import Image
from os import listdir, path
from pathlib import Path

NAME = 'Grim #'
COUNT = 2500
MAX_RARITY = 100

#NECKLACE_ACCESSORY = ['Scarf', 'Leather Necklace', 'Leather Spiked Necklace', 'Spiked Necklace', 'Rope', 'Leaves', 'Inflatable Wheel', 'Rescue Wheel', 'Tracking Necklace', 'Snake']

def find_csv_filenames(path_to_dir, suffix=".csv"):
    filenames = listdir(path_to_dir)
    return [filename for filename in filenames if filename.endswith(suffix)]

def get_layers():
    files = find_csv_filenames('Traits')
    files.sort()
    layers = []
    for f in files:
        if f.find('Specials') > -1:
            continue
        filename = f'Traits/{f}'
        with open(filename, newline='') as csvfile:
            name = Path(filename).resolve().stem[2:]
            trait_path = f"./Traits/{name}"
            values = []
            weights = []
            filename = []
            reader = csv.reader(csvfile, delimiter=',')
            for row in reader:
                filename.append(row[0])
                weights.append(int(row[1]))
                values.append(row[2])
            layers.append({
                "name": name,
                "values": values,
                "trait_path": trait_path,
                "filename": filename,
                "weights": weights
            })
    return {
        "layers": layers,
        "name": NAME
    }

def get_specials():
    filename = f'Traits/Specials.csv'
    result = {}
    if not path.isfile(filename):
        return result
    with open(filename, newline='') as csvfile:
        reader = csv.reader(csvfile, delimiter=',')
        first_line = True
        traits = []
        for row in reader:
            if first_line:
                traits = row
                first_line = False
            else:
                special = {}
                number = 0
                for i, trait in enumerate(traits):
                    if trait == 'Number':
                        number = int(row[i])
                    else:
                        special[trait] = row[i]
                result[number] = special
    return result

def is_not_valid_image(image, specials):
    # if image['Accessory'].find('Tattoo') > -1 and image['Eyes'] == 'Skeleton' or \
    #    image['Accessory'].find('Tattoo') > -1 and image['Body'].find('Sahara') > -1 or \
    #    image['Accessory'].find('Snake') > -1 and image['Mouth'] == 'Handlebar Moustache' or \
    #    image['Accessory'].find('Snake') > -1 and image['Mouth'] == 'Van Dyke Beard' or \
    #    image['Accessory'].find('Snake') > -1 and image['Mouth'] == 'Skeleton' or \
    #    image['Clothes'] == 'Tie' and image['Accessory'] in NECKLACE_ACCESSORY:
    #      return True

    if image in specials.values():
        return True

    return False


def create_new_image(i, all_images, config, specials):
    new_image = {}

    if i in specials:
        return specials[i]

    for layer in config["layers"]:
      new_image[layer["name"]] = random.choices(layer["values"], layer["weights"])[0]

    if new_image in all_images or is_not_valid_image(new_image, specials):
        return create_new_image(i, all_images, config, specials)
    else:
        return new_image

def generate_unique_images(amount, config, specials):
  counter_dict = {}
  for item in config['layers']:
    name = item['name']
    values = item['values']
    counter_dict[name] = {}
    for i in values:
      counter_dict[name][i] = 0

  pad_amount = len(str(amount))
  trait_files = {
  }
  for trait in config["layers"]:
    trait_files[trait["name"]] = {}
    for x, key in enumerate(trait["values"]):
      trait_files[trait["name"]][key] = trait["filename"][x]

  all_images = []
  for i in range(amount):
    new_trait_image = create_new_image(i+1, all_images, config, specials)
    all_images.append(new_trait_image)

  i = 1
  for item in all_images:
      item["tokenId"] = i
      i += 1

  for image in all_images:
    for layer in image:
      if layer != 'tokenId':
        layer_type = image[layer]
        counter_dict[layer][layer_type] = counter_dict[layer][layer_type] + 1

  with open('./metadata/count.json', 'w') as outfile:
    json.dump(counter_dict, outfile, indent=4)

  max_rarity = None
  min_rarity = None
  max_image = None
  min_image = None
  for image in all_images:
    total_weight = 0
    for key in image:
        if key != "tokenId":
            count = counter_dict[key][image[key]]
            weight = 100 - count/COUNT * 100
            total_weight = total_weight + weight
    overall_rarity = 100 - ((100 * len(counter_dict)) - total_weight)
    image['rarity'] = overall_rarity
    if min_rarity is None or min_rarity > overall_rarity:
        min_rarity = overall_rarity
        min_image = image
    if max_rarity is None or max_rarity <= overall_rarity:
        max_rarity = overall_rarity
        max_image = image

  sum = 0
  for image in all_images:
      rarity = image['rarity']
      if rarity == min_rarity:
          rarity = 1
      elif rarity == max_rarity:
          rarity = MAX_RARITY
      else:
          rarity = max(int(rarity - min_rarity), 1)
          rarity = min(max(int(rarity / (max_rarity - min_rarity) * MAX_RARITY), 1), MAX_RARITY)
      image['rarity'] = rarity
      sum += rarity

  for i, token in enumerate(all_images):
    attributes = []
    for key in token:
      if key != "tokenId" and key != 'rarity':
        attributes.append({"type": key, "value": token[key], "rarity": counter_dict[key][token[key]]})
    token_metadata = {
        "name":  config["name"] + str(i+1),
        "description":  config["name"] + str(i+1) + " of " + str(COUNT),
        "file": "ipfs://ipfs/{ipfs_hash}/" + str(i+1) + '.png',
        "overall_rarity": token['rarity'],
        "attributes": attributes
    }
    with open('./metadata/' + str(i+1) + '.json', 'w') as outfile:
        json.dump(token_metadata, outfile, indent=4)

  with open('./metadata/all-objects.json', 'w') as outfile:
    json.dump(all_images, outfile, indent=4)

  i = 1
  for item in all_images:
    print('Generating image ' + str(i))
    i = i + 1
    layers = []
    for index, attr in enumerate(item):
      if attr != 'tokenId' and attr != 'rarity':
        layers.append([])
        layers[index] = Image.open(f'{config["layers"][index]["trait_path"]}/{attr} {trait_files[attr][item[attr]]}.png').convert('RGBA')

    if len(layers) == 1:
      rgb_im = layers[0].convert('RGBA')
      file_name = str(item["tokenId"]) + ".png"
      rgb_im.save("./images/" + file_name)
    elif len(layers) == 2:
      main_composite = Image.alpha_composite(layers[0], layers[1])
      rgb_im = main_composite.convert('RGBA')
      file_name = str(item["tokenId"]) + ".png"
      rgb_im.save("./images/" + file_name)
    elif len(layers) >= 3:
      main_composite = Image.alpha_composite(layers[0], layers[1])
      layers.pop(0)
      layers.pop(0)
      for index, remaining in enumerate(layers):
        main_composite = Image.alpha_composite(main_composite, remaining)
      rgb_im = main_composite.convert('RGBA')
      file_name = str(item["tokenId"]) + ".png"
      rgb_im.save("./images/" + file_name)


generate_unique_images(COUNT, get_layers(), get_specials())
