import json
import random
from PIL import Image


def is_in_special_images(image):
    special_images = [
        {'Top': 'Hathor HQ', 'Road': 'Hathor Truck', 'Left': 'Hathor Labs', 'Right': 'Hathor Green', 'Bottom': 'Merged Mines'}, #Hathor
        {'Top': 'Industry', 'Road': 'Industry', 'Left': 'Industry', 'Right': 'Industry', 'Bottom': 'Industry'}, #Industry
        {'Top': 'Abandoned', 'Road': 'Empty Road', 'Left': 'Abandoned', 'Right': 'Abandoned', 'Bottom': 'Abandoned'}, #Abandoned
    ]

    special_images_without_road = [
        {'Top': 'Anubis', 'Left': 'Horus', 'Right': 'Hathor Swap', 'Bottom': 'Scarab NFT', 'Billboard': 'Hathormon'},  #Egypt
    ]

    for special_image in special_images:
        if(image['Top'] == special_image['Top'] and image['Road'] == special_image['Road'] and image['Left'] == special_image['Left'] and
           image['Right'] == special_image['Right'] and image['Bottom'] == special_image['Bottom']):
            return True

    for special_image in special_images_without_road:
        if(image['Top'] == special_image['Top'] and image['Left'] == special_image['Left'] and
           image['Right'] == special_image['Right'] and image['Bottom'] == special_image['Bottom']):
            return True

    return False

def create_new_image(all_images, config):
    new_image = {}
    for layer in config["layers"]:
      new_image[layer["name"]] = random.choices(layer["values"], layer["weights"])[0]

    if new_image in all_images or is_in_special_images(new_image):
        return create_new_image(all_images, config)
    else:
        return new_image

def generate_unique_images(amount, config):
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
    new_trait_image = create_new_image(all_images, config)
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


  for i, token in enumerate(all_images):
    attributes = []
    for key in token:
      if key != "tokenId":
        attributes.append({"type": key, "value": token[key]})
    token_metadata = {
        "name":  config["name"] + str(i+1),
        "description":  config["name"] + str(i+1) + " of 11,111",
        "file": "ipfs://ipfs/{ipfs_hash}/" + str(i+1) + '.png',
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
      if attr != 'tokenId':
        layers.append([])
        layers[index] = Image.open(f'{config["layers"][index]["trait_path"]}/{trait_files[attr][item[attr]]}.png').convert('RGBA')

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

generate_unique_images(1, {
    "layers": [
        {
            "name": "Top",
            "values":    ["Hathor Gum"],
            "trait_path": "./layers/top",
            "filename": ["11_gum"],
            "weights":  [1]
        },
        {
            "name": "Road",
            "values": ["Middle Road"],
            "trait_path": "./layers/road",
            "filename": ["4_common"],
            "weights":  [1]
        },
        {
            "name": "Left",
            "values": ["Middle Left St."],
            "trait_path": "./layers/left",
            "filename": ["3_common"],
            "weights":  [1]
        },
        {
            "name": "Right",
            "values": ["Right St."],
            "trait_path": "./layers/right",
            "filename": ["1_common"],
            "weights":  [1]
        },
        {
            "name": "Bottom",
            "values": ["Police Station"],
            "trait_path": "./layers/bottom",
            "filename": ["12_police"],
            "weights":  [1]
        },
        {
            "name": "Billboard",
            "values": ["Bitcoin Pizza Day"],
            "trait_path": "./layers/billboard",
            "filename": ["2_pizza_day"],
            "weights":  [1]
        },
        {
            "name": "Special",
            "values": ["UFO"],
            "trait_path": "./layers/special",
            "filename": ["12_ufo"],
            "weights":  [1]
        }
    ],

    "name": "Hathor Tree #"
})

#Additional layer objects can be added following the above formats. They will automatically be composed along with the rest of the layers as long as they are the same size as eachother.
#Objects are layered starting from 0 and increasing, meaning the front layer will be the last object. (Branding)
