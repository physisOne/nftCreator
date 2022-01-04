def create_maps(config):
    layers = config["layers"]
    for layer in layers:
        name = layer["name"]
        map_name = 'map' + name;
        print('private static final Map<String, String> ' + map_name + " = new HashMap<>();")
        i = 0
        for value in layer["values"]:
            image = layer["filename"][i]
            print(map_name + '.put("' + value + '", "' + image + '");')
            i = i+1


create_maps({
    "layers": [
        {
            "name": "Top",
            "values":    ["Top St.", "Upper St.", "North St.", "First St.", "Up St.", "Head St.", "High St.", "Stadium", "Industry", "Abandoned", "Hathor Gum",     "Nile Swap", "9Block",     "HTR/FDT",   "Anubis",    "Cathor",    "Hathor HQ"],
            "trait_path": "./layers/top",
            "filename": ["1_common", "2_common", "3_common", "4_common", "5_common", "6_common", "7_common", "8_stadium", "9_industry", "10_abandoned", "11_gum", "12_nileswap", "13_9block", "14_htrfdt", "15_anubis", "16_cathor", "17_hathor_hq"],
            "weights":  [1250,       1200,       1150,       1100,       1050,       1000,       950,         650,         600,          500,           400,       350,           300,         250,         200,         100,         50]
        },
        {
            "name": "Road",
            "values": ["Main Road", "New Road", "Old Road", "Middle Road", "Common Road", "Central Road", "Inner Road", "Empty Road", "Industry", "Police & Ambulance", "Traffic Jam", "Construction", "Car Crash", "Military Convoy", "Hathor Truck"],
            "trait_path": "./layers/road",
            "filename": ["1_common", "2_common", "3_common", "4_common", "5_common", "6_common", "7_common", "8_empty", "9_industry", "10_police_ambulance", "11_traffic", "12_construction", "13_car_crash", "14_army", "15_htr_truck"],
            "weights":  [1500,       1400,       1300,       1200,       1100,       1050,       950,     700,               600,          500,                300,           200,               150,            100,         50]
        },
        {
            "name": "Left",
            "values": ["Left St.", "West St.", "Middle Left St.", "Left Wing St.", "Left Side St.", "Second St.", "Even St.", "Playground", "Industry", "Abandoned", "Church & Graveyard",     "Hathor Cards", "Afferolab.Flow",     "ManCave Men",   "Horus",    "Databoi",    "Hathor Labs"],
            "trait_path": "./layers/left",
            "filename": ["1_common", "2_common", "3_common", "4_common", "5_common", "6_common", "7_common", "8_playground", "9_industry", "10_abandoned", "11_graveyard", "12_hathor_cards", "13_affero", "14_mancave", "15_horus", "16_databoi", "17_hathor_labs"],
            "weights":  [1250,       1200,       1150,       1100,       1050,       1000,       950,         650,            600,          500,             450,            400,            300,           200,         150,            100,         50]
        },
        {
            "name": "Right",
            "values": ["Right St.", "East St.", "Middle Right St.", "Right Wing St.", "Right Side St.", "Third St.", "Odd St.", "Industry", "Abandoned", "Parking House", "Lake",     "House in Fire", "Panth Protocol",     "Hathor City",   "Cactibles",    "Hathor Bullz Club",    "Hathor Swap", "Hathor Green"],
            "trait_path": "./layers/right",
            "filename": ["1_common", "2_common", "3_common", "4_common", "5_common", "6_common", "7_common", "8_industry", "9_abandoned", "10_car_park_house", "11_lake", "12_fire_house", "13_panth_protocol", "14_hathor_city", "15_cactibles", "16_hathor_bullz_club", "17_hathor_swap", "18_hathor_green"],
            "weights":  [1250,       1200,       1150,       1100,       1050,       1000,       950,         600,         500,            500,                 400,           350,           300,                   250,            200,               150,                  100,                50]
        },
        {
            "name": "Bottom",
            "values": ["Bottom St.", "Base St.", "Down St.", "South St.", "Ground St.", "Last St.", "Fourth St.", "Shopping Mall", "Industry", "Fire Station",   "Abandoned",     "Police Station", "Hospital",     "Amusement Park",   "Airport",    "Military Base",    "Hathstract", "Vikings of Hathor", "Scarab NFT", "Merged Mines"],
            "trait_path": "./layers/bottom",
            "filename": ["1_common", "2_common", "3_common", "4_common", "5_common", "6_common", "7_common", "8_shopping_mall", "9_industry", "10_fire_station", "11_abandoned", "12_police", "13_hospital", "14_amusement_park", "15_airport", "16_military", "17_hathstract", "18_vikings_of_hathor", "19_scarab_nft", "20_merged_mines"],
            "weights":  [1050,       950,         900,         850,         850,       800,          800,         650,              600,            500,                 500,        500,           450,             400,            350,            300,             250,                200,               150,             50]
        },
        {
            "name": "Billboard",
            "values": ["None", "Bitcoin Pizza Day", "Zero Fees", "LFG!", "Industry", "The Education Help Foundation", "Egypt", "Visa", "Cathor", "@KianuPicanto", "@trondbjoroy", "@CryptoKaibaCorp", "@CryptoWizKing", "@FatManWithGems", "@_RN03xx_", "@wagmisaurus", "@CryptoMagnified", "@DreadBong0", "@BITCOINTRAPPER", "@CryptoTony__", "Abandoned", "Hathor Green", "Hathor"],
            "trait_path": "./layers/billboard",
            "filename": ["1_none", "2_pizza_day", "3_zero_fees", "4_lfg", "5_industry", "6_edu", "7_egypt", "8_visa", "9_cathor", "10_KianuPicanto", "11_trondbjoroy", "12_CryptoKaibaCorp", "13_CryptoWizKing", "14_FatManWithGems", "15_RN03xx_", "16_wagmisaurus", "17_CryptoMagnified", "18_DreadBong0", "19_BITCOINTRAPPER", "20_CryptoTony__", "21_abandoned", "22_hathor_green", "23_hathor_logo"],
            "weights":  [  1200,       1170,         1100,         950,       850,        750,          700,      650,       600,            450,               400,               350,                 330,                 300,            250,            210,                190,                170,               140,             120,                100,              70,                50]
        },
        {
            "name": "Special",
            "values": ["None", "Airplane", "Helicopter", "Lightning", "Balloon", "Paragliding Man", "Sport Plane", "Military Helicopter", "Tornado", "Satellite", "Space Shuttle", "UFO"],
            "trait_path": "./layers/special",
            "filename": ["1_none", "2_airplane", "3_helicopter", "4_lightning", "5_baloon", "6_paragliding", "7_sport_plane", "8_military_helicopter", "9_tornado", "10_satellite", "11_space_shuttle", "12_ufo"],
            "weights":  [10470,      100,            90,            80,            80,            70,             60,                   50,                40,            30,               20,            10]
        }
    ]
})
