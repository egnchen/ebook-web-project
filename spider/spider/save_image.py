import json
import requests
import hashlib
import os

books = json.load(open("spiders/programming.json"))

for idx, book in enumerate(books):
    image_url = book['img']
    # found out that they are all jpegs
    image_type = 'jpeg'
    image_file_name = f"images/{book['title']}.{image_type}"
    # use sha1 to generate file name
    sha1_hash = hashlib.sha1(book['title'].encode('utf-8')).hexdigest()
    new_file_name = f"images/{sha1_hash}.{image_type}"
    # rename legacy image file
    try:
        if os.path.isfile(new_file_name):
            books[idx]['img'] = new_file_name
            continue
        if os.path.isfile(image_file_name):
            print('-', end='')
            os.rename(image_file_name, new_file_name)
            books[idx]['img'] = new_file_name
            continue
    except Exception as e:
        print(e)
        continue
    
    image = requests.get(image_url)
    if image.ok:
        print(book['title'])
    else:
        print(book['title'], image)
    
    try:
        # save file
        # legacy file exists
        image_file = open(new_file_name, "wb")
        image_file.write(image.content)
        image_file.close()
        books[idx]['img'] = new_file_name
    except Exception as e:
        print(e)
        continue


new_books = json.dumps(books)
f = open('new_books.json', 'w')
f.write(new_books)
f.close()