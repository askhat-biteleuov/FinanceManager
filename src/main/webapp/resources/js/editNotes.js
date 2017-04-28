$(document).ready(function () {
   $('#editBtn').on('click', function () {
       $(this).closest('#row').find("#incomeNote").attr('contenteditable', 'true');
   })
});
