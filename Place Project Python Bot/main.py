"""
This is the python side of my twitter bot project
It is a simple loop that goes through the data file and posts the appropriate colours to twitter
Made by Elizabeth Norman, @spicedboi
"""

import tweepy
import time

auth = tweepy.OAuth1UserHandler('API KEY', 'API PRIVATE',
                                'ACCESS TOKEN', 'ACCESS TOKEN PRIVATE'
                                )
api = tweepy.API(auth)


def tweet():

    file = open("favourites.txt")

    for lines in file:
        lines = lines.strip()  # get rid of newline
        lines = str.lower(lines)
        filenames = lines.split(" ")
        for i in range(1, len(filenames)):
            filenames[i] = 'colorImages/' + filenames[i] + '.png'
        filenames[0] = make_date(filenames[0])
        media_ids = []
        for i in range(1, len(filenames)):
            res = api.media_upload(filenames[i])
            media_ids.append(res.media_id)

        api.update_status(filenames[0], media_ids=media_ids)
        time.sleep(900)


def make_date(date):
    date = date.replace('.txt', '')
    dates = date.split("-")
    date = "April " + dates[2] + " at " + dates[3] + ":" + dates[4]
    return date


if __name__ == '__main__':
    tweet()

