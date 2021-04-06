



import pandas as pd

df = pd.read_csv("ceps.csv", delimiter=",")




from geopy.geocoders import Nominatim
geolocator = Nominatim(user_agent="HeatMapAuto")


geocodes = []
for i in range (0,len(df.index)):    
    #texto = df['rua'][i] + "," + df['cidade'][i] + "," + df['estado'][i] + "," + df['pais'][i]
    
    location = geolocator.geocode(df['ceps'])
    geocodes.append([location.latitude,location.longitude])


import numpy as np

a = np.matrix(geocodes)
dfa = pd.DataFrame(a)

import os
import folium

print(folium.__version__)

from folium.plugins import HeatMap

m = folium.Map([48., 5.], tiles='stamentoner', zoom_start=6)

HeatMap(dfa).add_to(m)

m.save(os.path.join('results', 'Heatmap.html'))

m