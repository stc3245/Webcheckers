<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Player Sign In</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>Player Sign In</h1>

    <div class="navigation">
        <a href="/">my home</a>
    </div>

    <div class="navigation">
        <a href="/signin">sign in</a>
    </div>


    <#if signInError??>
        <p>
            ${errorMessage}
        </p>
    </#if>

    <form action="./" method="post">
        <br/>
        Sign in here.
        <br/>
        <input name="username" />
        <br/> <br/>
        <button type="submit">Sign In</button>
    </form>

</div>
</body>
</html>
