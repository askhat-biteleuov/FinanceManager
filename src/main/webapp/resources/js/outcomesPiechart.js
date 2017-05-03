$(document).ready(function () {
    LoadChart("2017-05-01", "2017-05-31", $('#rangeForm [name=accountName]').val())
    $('#rangeForm').submit(function (event) {
        event.preventDefault();
        $('#piechart').show();
        LoadChart($("#rangeForm [name=start]").val(), $("#rangeForm [name=end]").val(), $('#rangeForm [name=accountName]').val());
    });
});


function LoadChart(start, end, account) {
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
            "start": start,
            "end": end,
            "accountName": account
        })
    }).done(function (data) {
        drawChart(data);
    }).fail(function (error) {
        alert('FAIL ' + error);
    });
}