from typing import Optional, Dict, List, Mapping
import pandas as pd

from fastapi.encoders import jsonable_encoder

from fastapi import FastAPI
from pydantic import BaseModel

class User(BaseModel):
    lat: float
    long: float

class Car(BaseModel):
    lat: float
    long: float
    owner: str

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

def find_nearest(user: dict, cars: list):
    car_df = pd.DataFrame(jsonable_encoder(cars))
    print(car_df)
    distances = car_df.apply(
        lambda row: dist(user.lat, user.long, row['lat'], row['long']), axis=1)
    return car_df.loc[distances.idxmin(), 'owner']

@app.post("/findNearestCar/")
async def find_nearest_car(locations: LocationBundle):
    nearest_location = find_nearest(locations.user, locations.cars)
    return nearest_location