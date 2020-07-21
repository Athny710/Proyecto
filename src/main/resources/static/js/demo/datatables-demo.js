// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
    "language": {
      "lengthMenu": "Mostrar _MENU_ registros",
      "zeroRecords": "No se encontraron resultados",
      "info": "Mostrando página _PAGE_ de _PAGES_",
      "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
      "infoFiltered": "(Filtrado de _MAX_ registros en total)",
      "sSearch": "Buscar",
      "oPaginate":{
        "sFirst": "Primero",
        "sLast": "Último",
        "sNext": "Siguiente",
        "sPrevious": "Anterior"},
      "sProcessing": "Procesando...",
    }

  });
});

$(document).ready(function() {
  $('#dataTable2').DataTable({
    "language": {
      "lengthMenu": "Mostrar _MENU_ registros",
      "zeroRecords": "No se encontraron resultados",
      "info": "Mostrando página _PAGE_ de _PAGES_",
      "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
      "infoFiltered": "(Filtrado de _MAX_ registros en total)",
      "sSearch": "Buscar",
      "oPaginate":{
        "sFirst": "Primero",
        "sLast": "Último",
        "sNext": "Siguiente",
        "sPrevious": "Anterior"},
      "sProcessing": "Procesando...",
    }

  });
});
