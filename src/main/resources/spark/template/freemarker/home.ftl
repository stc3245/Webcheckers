<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">my home</a>
    </div>

      <div class="navigation">
          <a href="/signin">sign in</a>
      </div>

      ${errorMsg}<br />

      <#if signedIn>
            <br/>
          ${welcomeMessage}
            <br/>
            <br/>

            <br/>
            All users logged in:
            <#list users as user>
                <br>
                  <form action="./startGame" method="post">
                      <input type="submit" name="opponentName" value="${user}" />
                  </form>
            </#list>
            <br/>
      <#else>
        <br/>
        ${currentUserNum}
      </#if>
    
    <div class="body">
      <p>Welcome to the world of online Checkers.</p>
    </div>
    
  </div>
</body>
</html>
