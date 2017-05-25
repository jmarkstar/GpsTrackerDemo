
# GpsTrackerDemo

Se esta usando AlarmManager para despertar al servicio que obtendrá la 
localización para version de android menores a Android Lollipop(21), para versiones igual y mayores
se esta usando JobScheduler, el JobScheduler funciona muy bien en android LOLLIPOP Y M pero en 
las versiones de android N(24 y 25) JobScheduler soportar como tiempo minimo periodico de 15 minutos.

Despues de que el servicio hay obtenido la localización, mediante el LocalBroadcastReciever envía los datos
al activity para poder refrescar la lista.

ScreenShots

<img src="/screenshots/Screenshot1.png" width="400"/>
<img src="/screenshots/Screenshot2.png" width="400"/>