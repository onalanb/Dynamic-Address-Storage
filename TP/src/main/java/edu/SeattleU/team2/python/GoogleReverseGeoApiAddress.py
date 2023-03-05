import random
from faker import Faker
import mysql.connector
import googlemaps

fake = Faker()
# This API key isn't active
gmaps = googlemaps.Client(key='AIzaSyAAbk9u2sGqlUm5iq0qwUqgJTh7LOjJi3w')

# define cities for each country
countries = {
    'United States':
        {
            'Chicago': {'lat': 41.8781136, 'lng': -87.6297982},
            'New York': {'lat': 40.7127753, 'lng': -74.0059728},
            'Los Angeles': {'lat': 34.0522342, 'lng': -118.2436849},
            'Houston': {'lat': 29.7604267, 'lng': -95.3698028},
            'Phoenix': {'lat': 33.4483771, 'lng': -112.0740373},
            'Seattle': {'lat': 47.608013, 'lng': -122.335167},
            'Portland': {'lat': 45.5152, 'lng': -122.6784}
        },
    'Australia':
        {
            'Sydney': {'lat': -33.8688, 'lng': 151.2093},
            'Melbourne': {'lat': -37.8136, 'lng': 144.9631},
            'Brisbane': {'lat': -27.4698, 'lng': 153.0251},
            'Perth': {'lat': -31.9535, 'lng': 115.8570},
            'Adelaide': {'lat': -34.9285, 'lng': 138.6007}
        },
    'China':
        {
            'Shanghai': {'lat': 31.2304, 'lng': 121.4737},
            'Beijing': {'lat': 39.9042, 'lng': 116.4074},
            'Guangzhou': {'lat': 23.1291, 'lng': 113.2644},
            'Shenzhen': {'lat': 22.5431, 'lng': 114.0579},
            'Wuhan': {'lat': 30.5928, 'lng': 114.3055},
            'Chengdu': {'lat': 30.5723, 'lng': 104.0665}
        },
    'France':
        {
            'Paris': {'lat': 48.8566, 'lng': 2.3522},
            'Marseille': {'lat': 43.2965, 'lng': 5.3698},
            'Lyon': {'lat': 45.7640, 'lng': 4.8357},
            'Toulouse': {'lat': 43.6045, 'lng': 1.4442},
            'Nice': {'lat': 43.7102, 'lng': 7.2620},
            'Cognac': {'lat': 45.6910, 'lng': -.3287}
        },
    'Spain':
        {
            'Madrid': {'lat': 40.4168, 'lng': -3.7038},
            'Barcelona': {'lat': 41.3851, 'lng': 2.1734},
            'Valencia': {'lat': 39.4699, 'lng': -0.3763},
            'Seville': {'lat': 37.3891, 'lng': -5.9845},
            'Zaragoza': {'lat': 41.6488, 'lng': -0.8891}
        },
    'New Zealand':
        {
            'Auckland': {'lat': -36.8485, 'lng': 174.7633},
            'Wellington': {'lat': -41.2865, 'lng': 174.7762},
            'Christchurch': {'lat': -43.5321, 'lng': 172.6362},
            'Hamilton': {'lat': -37.7870, 'lng': 175.2793},
            'Tauranga': {'lat': -37.6861, 'lng': 176.1652}
        },
    'Germany':
        {
            'Berlin': {'lat': 52.5200, 'lng': 13.4050},
            'Hamburg': {'lat': 53.5511, 'lng': 9.9937},
            'Munich': {'lat': 48.1351, 'lng': 11.5820},
            'Frankfurt': {'lat': 50.1109, 'lng': 8.6821},
            'Cologne': {'lat': 50.9375, 'lng': 6.9603}},
    'United Kingdom':
        {
            'London': {'lat': 51.5074, 'lng': -0.1278},
            'Birmingham': {'lat': 52.4862, 'lng': -1.8904},
            'Glasgow': {'lat': 55.8642, 'lng': -4.2518},
            'Sheffield': {'lat': 53.3811, 'lng': -1.4701},
            'Manchester': {'lat': 53.4808, 'lng': -2.2426}
        },
    'Mexico':
        {
            'Mexico City': {'lat': 19.4326, 'lng': -99.1332},
            'Tijuana': {'lat': 32.5149, 'lng': -117.0382},
            'Monterrey': {'lat': 25.6866, 'lng': -100.3161},
            'Cancun': {'lat': 21.1619, 'lng': -86.8515},
            'Guadalajara': {'lat': 20.6597, 'lng': -103.3496}
        },
    'Canada':
        {
            'Toronto': {'lat': 43.6532, 'lng': -79.3832},
            'Montreal': {'lat': 45.5017, 'lng': -73.5673},
            'Vancouver': {'lat': 49.2827, 'lng': -123.1207},
            'Calgary': {'lat': 51.0486, 'lng': -114.0708},
            'Edmonton': {'lat': 53.5461, 'lng': -113.4938}}}
# connect to MySQL database


cnx = mysql.connector.connect(user='your-user-name', password='your-password',
                              host='127.0.0.1', database='TeamProject')
cursor = cnx.cursor()

# create table for addresses
table_columns = ['country', 'state_province', 'recipient', 'street_address', 'street_number', 'postal_code', 'city_town_locality', 'full_address']

clear_query = "TRUNCATE TABLE addresses"
cursor.execute(clear_query)

alter_table_query = "ALTER TABLE addresses "
if 'street_number' not in table_columns:
    alter_table_query += "ADD COLUMN street_number VARCHAR(255), "
if 'full_address' not in table_columns:
    alter_table_query += "ADD COLUMN full_address VARCHAR(255), "
alter_table_query = alter_table_query[:-1]
cursor.execute(alter_table_query)

cnx.commit()


for country, cities in countries.items():
    for city, location in cities.items():
        lat, lng = location['lat'], location['lng']
        location_str = f"{lat}, {lng}"
        places_result = gmaps.places_nearby(location=location_str, radius=1000, type='address')

        # check if there are any places found for the city
        if not places_result['results']:
            print(f"No places found for {city}, {country}")
            continue

        # retrieve the top 20 unique addresses for the city
        addresses = []
        for i in range(20):
            place = places_result['results'][i]
            # set the country parameter based on the country
            country_codes = {
                'United States': 'US',
                'China': 'CN',
                'France': 'FR',
                'Spain': 'ES',
                'Germany': 'DE',
                'United Kingdom': 'GB',
                'Mexico': 'MX',
                'Canada': 'CA',
                'Australia': 'AU',
                'New Zealand': 'NZ'
            }
            country_code = country_codes[country]

            # reverse geocode the location
            reverse_geocode_result = gmaps.reverse_geocode(
                (place['geometry']['location']['lat'], place['geometry']['location']['lng']),
                result_type='street_address',
                language='en'
            )

            # check if the result is empty
            if not reverse_geocode_result:
                continue

            # parse the address components and set the address fields
            address = {'country': country, 'state_province': '', 'recipient': fake.name(),
                       'street_address': '', 'street_number': '', 'postal_code': '', 'city_town_locality': '',
                       'full_address': ''}

            for component in reverse_geocode_result[0]['address_components']:
                types = component['types']
                long_name = component['long_name']
                if 'administrative_area_level_1' in types:
                    address['state_province'] = long_name
                elif 'postal_code' in types:
                    address['postal_code'] = long_name
                elif 'locality' in types or 'postal_town' in types or 'sublocality_level_1' in types or 'sublocality_level_2' in types:
                    address['city_town_locality'] = long_name
                elif 'route' in types:
                    address['street_address'] = long_name
                elif 'country' in types:
                    # set the country parameter in the address string
                    if not country_code:
                        country_code = long_name[:2]
                    address['country'] = long_name
                elif 'street_number' in types:
                    address['street_number'] = long_name if 'street_number' in address else ''
                elif 'address_number' in types:
                    address['street_number'] = long_name if 'street_number' not in address else ''

            # construct the full address string
            address_parts = [address.get('address_number', ''), address['street_address'],
                             address['city_town_locality'],
                             address['state_province'], address['postal_code'], address['country']]
            address['full_address'] = ', '.join([part for part in address_parts if part])

            # check if the address already exists in the list
            if address in addresses:
                continue
            else:
                addresses.append(address)
            print(address)
            # continue the loop until 20 unique addresses are found
            if len(addresses) == 20:
                break

        # insert addresses into database
        insert_query = f"INSERT INTO addresses ({', '.join(table_columns)}) VALUES "
        for address in addresses:
            values = ", ".join([repr(address[column]) for column in table_columns])
            insert_query += f"({values}), "
        insert_query = insert_query[:-2]

        cursor.execute(insert_query)
        cnx.commit()

select_query = "SELECT * FROM addresses"
cursor.execute(select_query)
result = cursor.fetchall()
for row in result:
    print(row)

cursor.close()
cnx.close()
