
#GpsTrackerDemo

Se esta usando AlarmManager para despertar al servicio que obtendrá la 
localización para version de android menores a Android Lollipop(21), para versiones igual y mayores
se esta usando JobScheduler, el JobScheduler funciona muy bien en android LOLLIPOP Y M pero en 
las versiones de android N(24 y 25) JobScheduler soportar como tiempo minimo periodico de 15 minutos.

En proceso...