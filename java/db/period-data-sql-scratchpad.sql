#these match the fields from PeriodData Java interface

select avg(dewpoint), avg(outsideHumidity), avg(barometer), avg(outsideTemperature), avg(averageWindSpeed), avg(windchill), max(dewpoint), max(highWindSpeed), max(outsideHumidity), max(barometer), max(highOutTemperature), max(windchill), min(dewpoint), min(outsideHumidity), min(barometer), min(LowOutTemperature), min(windchill), sum(rainfall) from newWeather.dmprecords d where d.date > '2006-01-14';