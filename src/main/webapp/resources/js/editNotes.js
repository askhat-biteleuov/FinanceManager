$(document).ready(function () {
    $('.notes').on('click', '.editBtn', function () {
        $(this).closest('.editBar').find('.saveBtn').show();
        $(this).closest('.editBar').find('.cancelBtn').show();
        $(this).hide();
        var note = $(this).closest('.tableRow').find(".note");
        var oldVal = $(this).closest('.editBar').find('.oldVal');
        oldVal.val(note.text());
        note.attr('contenteditable', 'true');
        note.focus();
    });

    $('.notes').on('keyup', function (event) {
        var esc = event.which === 27;
        var element = event.target;
        if (esc) {
            stopEdit(element);
            var oldVal = $(element).closest('.tableRow').find(".oldVal");
            $(element).text(oldVal.val());
        }
    });

    $('.cancelBtn').click(function () {
        var field = $(this).closest('.tableRow').find(".note");
        var oldVal = $(this).closest('.editBar').find(".oldVal");
        field.text(oldVal.val());
        stopEdit(field);
    });

    $('.notes').on('submit', '.saveNote', function (event) {
        event.preventDefault();
        var field = $(this).closest('.tableRow').find('.note');
        var newNote = field.text();
        $(this).find('[name=note]').attr('value', newNote);
        var form = $(this);
        var data = form.serializeArray();
        var objectifyData = objectifyForm(data);
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
            stopEdit(field)
        }).fail(function (error) {
            form.find(".with-errors").each(function () {
                $(this).addClass("fail");
                $(this).text("Введите корректные данные");
            });
        });
    });
});

function stopEdit(element) {
    $(element).attr('contenteditable', 'false');
    $(element).closest('.tableRow').find(".saveBtn").hide();
    $(element).closest('.tableRow').find(".cancelBtn").hide();
    $(element).closest('.tableRow').find(".editBtn").show();
}

function objectifyForm(formArray) {  //serialize data function
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}
