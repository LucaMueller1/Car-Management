from typing import Optional, Dict, List, Mapping
from fastapi.middleware.cors import CORSMiddleware
import uvicorn

import pandas as pd
import base64

from fastapi import FastAPI
from pydantic import BaseModel
import requests

app = FastAPI()


def get_as_base64(url):
    headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36'}
    return base64.b64encode(requests.get(url, headers=headers).content)


@app.get("/getLogo/")
async def find_nearest_car(brand: str):
    BASE_URL = "https://carlogos.eu/car-manufacturers-icons/car_brand_logos/"
    img_base64 = get_as_base64(BASE_URL + brand + ".jpg")
    print(BASE_URL + brand + ".jpg")
    return img_base64

origins = [
    '*'
]

app = CORSMiddleware(
    app=app,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

if __name__ == '__main__':
    uvicorn.run(app, host="0.0.0.0", port=8070)