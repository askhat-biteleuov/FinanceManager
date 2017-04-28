$(document).ready(function () {
    $('#rangeForm').submit(function (event) {
        event.preventDefault();
        $('#defaultPiechart').hide();
        $('#piechart').show();
        $.ajax({
            type: 'POST',
            beforeSend: function (request) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(header, token);
            },
            contentType: 'application/json; charset=UTF-8',
            url: '/account/page',
            data: JSON.stringify({
                "start": $("#rangeForm [name=start]").val(),
                "end": $("#rangeForm [name=end]").val(),
                "accountName": $('#rangeForm [name=accountName]').val()
            })
        }).done(function (data) {
            drawChart(data);
        }).fail(function (error) {
            alert('FAIL ' + error);
        });
    });
});