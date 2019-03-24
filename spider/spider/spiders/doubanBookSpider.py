# -*- coding: utf-8 -*-
import scrapy


class DoubanbookspiderSpider(scrapy.Spider):
    name = 'doubanBookSpider'
    allowed_domains = ['book.douban.com']
    tags = ['编程']

    def start_requests(self):
        for tag in self.tags:
            for i in range(0, 1700, 20):
                yield scrapy.Request(url = f'https://book.douban.com/tag/{tag}?start={i}',
                    callback = self.parse)
            
    def parse(self, response):
        items = response.css(".subject-item")
        for item in items:
            img = item.css(".pic>a>img").xpath("attribute::src").get()
            title = item.css(".info>h2>a::text").get()
            title = title.strip() if title else ''
            info = item.css(".pub::text").get()
            if info:
                info = info.strip().split("/")
                print(info)
                if len(info) == 5:
                    author = info[0] + ',' + info[1]
                else:
                    author = info[0]
                publisher, publish_date, price = info[-3:]
            else:
                author = publisher = publish_date = price = ""
            stars = item.css('.rating_nums::text').get()
            stars = stars if stars else ''
            desc = item.css(".info>p::text").get()
            desc = desc if desc else ''

            yield {
                'title': title,
                'author': author,
                'img': img,
                'publisher': publisher,
                'publish_date': publish_date,
                'price': price,
                'starts': stars,
                'desc': desc
            }