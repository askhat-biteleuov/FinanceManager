$(document).ready(function () {
    $('.modal').find('form').validator().on('submit', function (event) {
        $(".formFieldError").remove();
        if (!event.isDefaultPrevented()) {
            event.preventDefault();
            var form = $(this);
            var data = form.serializeArray();
            var objectifyData = serializeObject(data);
            var jsonData = JSON.stringify(objectifyData);
            $.ajax({
                type: 'POST',
                beforeSend: function (request) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    request.setRequestHeader(header, token);
                },
                contentType: 'application/json; charset=UTF-8',
                url: form.attr('action'),
                data: jsonData
            }).done(function (data) {
                $('.modal').modal('hide');//close modal window
                form.each(function () {
                    this.reset();
                });
                location.reload();
            }).fail(function (error) {
                var errors = error.responseJSON;
                for (var key in errors) { //foreach map
                    var formFieldError = "<div class=\"formFieldError alert alert-danger\" id=\"" + key + "Id\">" +
                        errors[key] + "</div>"; //new div
                    form.find("[name^='" + key + "']").before(formFieldError); //put div before input
                }
            })
        }
    });
    function objectifyForm(formArray) {  //serialize data function
        var returnArray = {};
        for (var i = 0; i < formArray.length; i++) {
            returnArray[formArray[i]['name']] = formArray[i]['value'];
        }
        return returnArray;
    }

    function serializeObject (formArray){
        var obj = {};
        $.each(formArray, function() {
            var value = this.value || '';
            if (/^\d+$/.test(value))
                value = +value;

            if (obj[this.name] !== undefined) {
                if (!obj[this.name].push) {
                    obj[this.name] = [obj[this.name]];
                }
                obj[this.name].push(value);
            } else {
                obj[this.name] = value;
            }
        });
        return obj;
    };
});
