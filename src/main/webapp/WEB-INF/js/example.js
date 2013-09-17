$(document).ready(function(){
  $.ajaxSetup({
    "beforeSend": function(xhr) {
      xhr.overrideMimeType("text/plain; charset=x-user-defined");
    },
    "converters": {
      '* msgpack': function(packed) {
        return msgpack.unpack(packed);
      }
    },
  });

  $.get("/msgpackdemo", function(data) {
    var text = "Customer: name = " + data.name + ", age = " + data.age;
    $("#result").text(text);
  }, "msgpack");
});