

def replace():
    for i in range(11111):
        print('replace file ' + str(i))
        with open('./metadata/' + str(i+1) + '.json', 'r') as file:
            data = file.read()
        data = data.replace('{ipfs_hash}', 'QmVerRBCdXyajoM6C5tLycBVMCZu4oPwjfjAf5byR1JvHW')

        with open('./metadata/' + str(i+1) + '.json', 'w') as file:
            file.write(data)

replace()
