<html>
<head>
  <title>Coronavirus Dashboard</title>
  <meta name="description" content="Aktuelle Informationen rund um die globale Ausbreitung vom Coronavirus.">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://fonts.googleapis.com/css?family=Asap:400,400i,500,500i,600,600i,700,700i&display=swap"
        rel="stylesheet">
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=UA-162036687-1"></script>
  <script src="analytics.js"></script>
  <link rel="stylesheet" type="text/css" href="style.css"/>
  <link rel="stylesheet" type="text/css" href="tables.css"/>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>

<body class="defaultFont">
<div class="header row p-3">
  <div class="col-lg-5">
    <h1 class="bigger-on-desktop">
      <a style="color: inherit;text-decoration: none;" href="/">Corona Dashboard</a>
    </h1>
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
<div>
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
          <span class="alignleft" style="white-space: nowrap">
            <img src="https://www.countryflags.io/${country.iso()}/flat/24.png" alt="Flag"/>
            <b style="margin-left: 5px">
              ${country.name()}
            </b>
          </span>
        </td>
        <td style="text-align: center" class="main-table">${country.infectionInformation().confirmed()} <small style="color: green">+${country.proliferationSinceYesterday().absolute()}</small></td>
        <td style="text-align: center" class="main-table">${country.infectionInformation().deaths()}</td>
        <td style="text-align: center" class="main-table">${country.infectionInformation().recovered()}</td>
      </tr>
    </#list>
  </table>
</div>
<div class="footer row p-3">
  <div class="col-lg-6">
    <p>&copy; Copyright 2020, Johannes Haberlah</p>
    <p><a style="color:inherit;" href="/impressum">Impressum</a></p>
    <p><a style="color:inherit;" href="/privacy">Datenschutzerklärung</a></p>
    <p>Alle Daten werden von der John Hopkins University bereitgestellt und aktualisieren sich stündlich.</p>
  </div>
</div>
</body>

</html>