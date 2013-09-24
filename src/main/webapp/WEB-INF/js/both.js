$(document).ready(function() {
  $.ajaxPrefilter(function(options) {
    var x = options;
    if ($.inArray("msgpack-send", options.dataTypes) != -1) {
      var toString = true;
      var packed = msgpack.pack(options.data, toString);
      var encoded = $.base64.encode(packed);
      options.data = encoded;
    }
  });

  $.ajaxSetup({
    "converters": {
      "* base64": function(encoded) {
        var data = $.base64.decode(encoded);
        return data;
      },
      "* msgpack": function(packed) {
        var unpacked = msgpack.unpack(packed);
        return unpacked;
      },
      "* msgpack-send": true
    }
  });

  $("#submit").click(function() {
    var data = $("#text").val();
    $.post("/api/both", data, function(text) {
      $("#result").text(text);
    }, "base64 msgpack msgpack-send");
  });
});