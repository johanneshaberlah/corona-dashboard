<html>

<head>
    <title>${country.name()}</title>
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Lato" />
</head>

<#if country ??>
    <h1 style="text-align: center; font-size: 300%; font-family: Lato">
        ${country.name()} <img style="height: 20%; width: 20%; margin-left: 100px" src="https://restcountries.eu/data/${country.iso()?lower_case}.svg">
    </h1>
<#else>
    <h2>Das Land existiert nicht.</h2>
</#if>

</html>