<html>
<head>
    <title>Coronavirus Dashboard</title>
    <meta name="description" content="Aktuelle Informationen rund um die globale Ausbreitung vom Coronavirus.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Asap:400,400i,500,500i,600,600i,700,700i&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>

<body class="defaultFont">
<div class="header row p-3">
    <div class="col-lg-5">
        <h1 style="font-size: 400%">Corona Dashboard</h1>
        <sub style="font-size: 130%;line-height: normal">
            <p class="hide-on-phone">Aktuelle Informationen rund um die weltweite Ausbreitung
            vom Coronavirus (SARS-CoV-2/COVID-19)</p>
        </sub>
    </div>
    <div class="col-lg-3"></div>
    <div class="col-lg-4">
        <table class="stats-table align-on-desktop-right-phone-left">
            <tr>
                <td class="bold-on-desktop">Infizierte Personen:</td>
                <td>${global.confirmed()}</td>
            </tr>
            <tr>
                <td class="bold-on-desktop">Verstorbene Personen:</td>
                <td>${global.deaths()}</td>
            </tr>
            <tr>
                <td class="bold-on-desktop">Geheilte Personen:</td>
                <td>${global.recovered()}</td>
            </tr>
        </table>
    </div>
</div>
<table class="main-table">
    <tr class="main-table">
        <th class="main-table">Land</th>
        <th class="main-table">Infizierte</th>
        <th class="main-table">Verstorbene</th>
        <th class="main-table">Geheilte</th>
    </tr>
    <#list topCountries as country>
        <tr class="main-table">
            <td class="main-table">
                <img src="https://www.countryflags.io/${country.shortcut()}/flat/24.png" alt="Flag"/>
                <b style="margin-left: 5px">
                    <a style="color:inherit;" href="/country/${country.name()}">${country.name()}</a>
                </b>
            </td>
            <td class="main-table" style="text-align: center">${country.infectionInformation().confirmed()}</td>
            <td class="main-table" style="text-align: center">${country.infectionInformation().deaths()}</td>
            <td class="main-table" style="text-align: center">${country.infectionInformation().recovered()}</td>
        </tr>
    </#list>
</table>
</body>

</html>