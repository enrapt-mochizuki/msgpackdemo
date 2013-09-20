$(document).ready(function() {
  $.ajaxPrefilter("msgpack-send", function(options) {
    var toString = true;
    var packed = msgpack.pack(options.data, toString);
    var encoded = btoa(packed)
    options.data = encoded;
  });

  $.ajaxSetup({
    "converters": {
      "* msgpack-send": true
    }
  });

  $("#submit").click(function() {
    var data = $("#text").val();
    $.post("/api/upload", data, function(text) {
      $("#result").text(text);
    }, "msgpack-send");
  });
});