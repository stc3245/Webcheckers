<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
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
         <div class="alert">
             <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
             ${errorMessage}<br />
         </div>
    </#if>

    <center>
        <form action="./" method="post">
            <h3>
                Sign-in With Username
            </h3>
            <input name="username" />
            <br/>
            <button type="submit">Sign In</button>
        </form>
    </center>
    <br>

</div>
</body>
</html>
