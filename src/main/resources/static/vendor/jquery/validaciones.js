$(document).ready(function () {
    $('#myform').validate({
        rules: {

            nombre: {
                required: true,
                maxlength: 45
            },

        },
        messages: {

            nombre: {
                required: "*Debe asignar un nombre a la tienda",
                maxlength: "*El nombre de la tienda no debe exceder los 45 caracteres"
            },

        },
        submitHandler: function (form) {
            register();
        }
    });
});

