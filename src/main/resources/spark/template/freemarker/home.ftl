<!DOCTYPE html>
<head>
    <meta http-equiv="refresh" content="10;URL='/'" />
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">my home</a>
    </div>

    <#if signedIn>
        <div class="navigation">
            <a href="/signout">sign out</a>
        </div>
    <#else>
          <div class="navigation">
              <a href="/signin">sign in</a>
          </div>
    </#if>



          <#if error>
             <div class="alert">
                 <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                 ${errorMsg}<br />
             </div>
          <#else>
              ${errorMsg}<br />
          </#if>


      <#if signedIn>

          ${welcomeMessage}
            <br/>

          <#if gameEnded>
            ${gameMessage}
          </#if>

          <center><h2>Click user to play with them!</h2></center>
            <#list users as user>
                <br>
            <center>
            <svg id="i-user" viewBox="0 0 32 32" width="32" height="32" fill="none" stroke="currentcolor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
                <path d="M22 11 C22 16 19 20 16 20 13 20 10 16 10 11 10 6 12 3 16 3 20 3 22 6 22 11 Z M4 30 L28 30 C28 21 22 20 16 20 10 20 4 21 4 30 Z" />
            </svg>
                  <form action="./startGame" method="post">
                      <input type="submit" name="opponentName" value="${user}" />
                  </form>
            </center>
            </#list>
            <br />
            <center><h3>Bots!</h3></center>
          <#list bots as bot>
            <center>
                <svg id="i-paperclip" viewBox="0 0 32 32" width="32" height="32" fill="none" stroke="currentcolor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
                    <path d="M10 9 L10 24 C10 28 13 30 16 30 19 30 22 28 22 24 L22 6 C22 3 20 2 18 2 16 2 14 3 14 6 L14 23 C14 24 15 25 16 25 17 25 18 24 18 23 L18 9" />
                </svg>
                <form action="./startGame" method="post">
                    <input type="submit" name="opponentName" value="${bot}" />
                </form>
            </center>
          </#list>
            <br/>
      <#else>
        <br/>
        ${currentUserNum}
      </#if>
  </div>
</body>
</html>
