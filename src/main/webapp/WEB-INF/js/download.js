$(document).ready(function(){
  $.ajaxSetup({
    "converters": {
      "* base64": function(encoded) {
        var data = $.base64.decode(encoded);
        return data;
      },
      "* msgpack": function(packed) {
        var unpacked = msgpack.unpack(packed);
        return unpacked;
      }
    }
  });

  $.getMsgPack = function(url, data, callback) {
    return $.get(url, data, callback, "base64 msgpack");
  };

  $.getMsgPack("/api/download", function(data) {
    var text = "Customer: name " + data.name + ", age = " + data.age;
    $("#result").text(text);
  });
});