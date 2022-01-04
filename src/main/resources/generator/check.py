import os.path

def check():
    for i in range(11111):
        if not os.path.isfile('./metadata/' + str(i+1) + '.json'):
            print('./metadata/' + str(i+1) + '.json is not a file!')
        if not os.path.isfile('./images/' + str(i+1) + '.png'):
            print('./images/' + str(i+1) + '.png is not a file!')

check()
