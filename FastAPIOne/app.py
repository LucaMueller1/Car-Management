from typing import Optional, Dict, List, Mapping
import pandas as pd

from fastapi.encoders import jsonable_encoder

from fastapi import FastAPI
from pydantic import BaseModel
import requests

class User(BaseModel):
    id: float
    lat: float
    long: float

class Car(BaseModel):
    lat: float
    long: float
    id: int
    

class LocationBundle(BaseModel):
    user: User
    cars: List[Car]

app = FastAPI()

from math import radians, cos, sin, asin, sqrt
def dist(lat1, long1, lat2, long2):
    """
    Calculate the great circle distance between two points 
    on the earth (specified in decimal degrees)
    """
    # convert decimal degrees to radians 
    lat1, long1, lat2, long2 = map(radians, [lat1, long1, lat2, long2])
    # haversine formula 
    dlon = long2 - long1 
    dlat = lat2 - lat1 
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a)) 
    # Radius of earth in kilometers is 6371
    km = 6371* c
    return km

def find_nearest(latitude, longitude, cars: list):
    distances = [{"id": car["id"], "dist": dist(latitude, longitude, car["latitude"], car["longitude"])} for car in cars]
    return distances

@app.get("/findNearestCar/")
async def find_nearest_car(user_id: int, latitude: float, longitude: float):
    cars = requests.get("http://localhost:8080/api/v1/car/rentable").json()
    nearest_location = find_nearest(latitude, longitude, cars)

    return min(nearest_location, key=lambda x:x['dist'])