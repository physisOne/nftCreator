NFTS_COUNT = 6371
IPFS_HASH = 'QmZKK4cMdEvaZjrPU35rbMFCdFKg5tojBzqCDN7TzT9gre'

def replace():
    for i in range(NFTS_COUNT):
        print('replace file ' + str(i+1))
        with open('./metadata/' + str(i+1) + '.json', 'r') as file:
            data = file.read()
        data = data.replace('{ipfs_hash}', IPFS_HASH)

        with open('./metadata/' + str(i+1) + '.json', 'w') as file:
            file.write(data)

replace()
