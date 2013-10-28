<html>
  <head>
    <title>both</title>
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="/js/lib/jquery.base64.js"></script>
    <script src="/js/lib/msgpack.js"></script>
    <script src="/js/both.js"></script>
  </head>
  <body>
    <h2>both</h2>
    <label for="customer_name">Customer</label>
    <select name="customer_name" id="customer_name">
      <option value="Sherlock Shellingford" selected>Sherlock Shellingford</option>
      <option value="Cordelia Glauca">Cordelia Glauca</option>
    </select>
    <label for="request_mime_type">Request MIME Type</label>
    <select name="request_mime_type" id="request_mime_type">
      <option value="json" selected>json</option>
      <option value="msgpack">msgpack</option>
    </select>
    <label for="response_mime_type">Response MIME Type</label>
        <select name="response_mime_type" id="response_mime_type">
          <option value="json" selected>json</option>
          <option value="msgpack">msgpack</option>
        </select>
    <button type="submit" id="submit">submit</button>
    <div id="result"></div>
  </body>
</html>