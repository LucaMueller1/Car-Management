from typing import Optional, Dict, List, Mapping
import pandas as pd

from fastapi import FastAPI
from pydantic import BaseModel
import requests

app = FastAPI()


@app.get("/getLogoUrl/")
async def find_nearest_car(brand: str):
    manufacturers = requests.get("http://private-anon-4d57608bd9-carsapi1.apiary-mock.com/manufacturers").json()
    return next(iter([manufacturer["img_url"] for manufacturer in manufacturers if manufacturer["name"] == brand]), {})