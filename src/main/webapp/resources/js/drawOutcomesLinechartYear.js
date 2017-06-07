google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(refreshLineChartYear);
function drawLineChartYear(data) {

    var dataTable = new google.visualization.DataTable();

    dataTable.addColumn('date', 'Месяц');

    for (var type_ in data[1]) {
        dataTable.addColumn('number', type_)
    }

    var numOfTypes = Object.keys(data[1]).length;

    for (var month in data) {
        var dataArr = [numOfTypes + 1];
        dataArr[0] = new Date($('#yearSelectYear').val(), month-1);
        var i = 1;
        for (var type in data[month]) {
            dataArr[i] = (data[month])[type];
            i++;
        }
        dataTable.addRow(dataArr);
    }


    var options = {

        chart: {
            title: "Сумма расходов по категориям за месяц"

        },
        backgroundColor: 'transparent',
        hAxis: {
            viewWindowMode: 'explicit'
        }
    };

    var chart = new google.charts.Line(document.getElementById('linechartYear'));
    chart.draw(dataTable, google.charts.Line.convertOptions(options));
}

function refreshLineChartYear() {

    var types = [];
    $('.checkboxTypesYear input:checked').each(function () {
        types.push($(this).val());
    });
    LoadLineChartYear($("#linechartFormYear [name=year]").val(),
        $('#linechartFormYear [name=accountName]').val(), types);
}
