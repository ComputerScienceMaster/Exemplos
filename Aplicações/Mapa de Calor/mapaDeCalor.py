
import pandas as pd
import numpy as np

# import CSV
df = pd.read_csv("enderecos.csv", delimiter=",")


from geopy.geocoders import Nominatim
geolocator = Nominatim(user_agent="HeatMapAuto")


## pass addresses to API to convert in latitude and longitude
geocodes = []
for i in range (0,len(df.index)):    
    texto = df['rua'][i] + "," + df['cidade'][i] + "," + df['estado'][i] + "," + df['pais'][i]
    location = geolocator.geocode(texto)
    if (location is not None):
        geocodes.append([location.latitude,location.longitude])


# parse geocodes to dataframe
dfa = pd.DataFrame(geocodes)

# import folium and OS libraries
import os
import folium
print(folium.__version__)
from folium.plugins import HeatMap


m = folium.Map([48., 5.], tiles='stamentoner', zoom_start=6)
HeatMap(dfa).add_to(m)

# Save folium map in HTML
m.save(os.path.join('results', 'Heatmap.html'))
