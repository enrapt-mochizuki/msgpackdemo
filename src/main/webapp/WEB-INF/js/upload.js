$(document).ready(function() {
  $.ajaxPrefilter("msgpack-send", function(options) {
    var toString = true;
    var packed = msgpack.pack(options.data, toString);
    var encoded = $.base64.encode(packed);
    options.data = encoded;
  });

  $.ajaxSetup({
    "converters": {
      "* msgpack-send": true
    }
  });

  $("#submit").click(function() {
    var data = $("#text").val();
    var method = $("#method").val();
    $.ajax({
      "url": "/api/upload",
      "method": method,
      "data": data,
      "dataType": "msgpack-send"
    }).done(function(text) {
      $("#result").text(text);
    });
  });
});