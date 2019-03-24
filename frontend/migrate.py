import json
from PIL import Image

books = json.load(open(r"E:\JavaProjects\ebook\spider\spider\new_books.json"))

for idx, book in enumerate(books):
    print(idx)
    try:
        img = Image.open("E:\\JavaProjects\\ebook\\frontend\\vuejs\\public\\" + book["img"].replace('/','\\'))
        books[idx]["img"] = {
            "img": book["img"],
            "size": img.size
        }
    except Exception as e:
        print(e)
        books[idx]["img"] = {
            "img": book["img"],
            "size": (0,0)
        }
        continue

json.dump(books, open(r"E:\JavaProjects\ebook\spider\spider\new_books.json", "w"))