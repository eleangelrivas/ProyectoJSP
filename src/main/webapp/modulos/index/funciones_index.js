$(function(){//funcion anonima
        
        console.log("Jquery funcionando podemos ver"); 
        $("#cargar_tabla").empty().append("<p>Hola como estas</p>");
        cargar_datos();
        
        $(document).on("click","#boton_editar",function(e){
            e.preventDefault();
            
            $("#md_registro").modal("show");
        });
        $(document).on("click","#boton_eliminar",function(e){
            e.preventDefault();
            var id = $(this).attr("data-id");
            Swal.fire({
                title: '¿Desea eliminar el elemento?',
                html:'Esta acción no puede ser revertida',
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Si, eliinar',
                denyButtonText: 'No eliminar',
              }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                  eliminar_dato($(this).attr("data-id"));
                } else if (result.isDenied) {
                  Swal.fire('Acción cancelada por el usuairo', '', 'info')
                }
              })

            
        });
        
        
    });
    
    function eliminar_dato(el_id){
        mostrar_cargando("Procesando solicitud","Espere mientras se elimina la información")
	var datos = {"consultar_datos":"si_eliminalo",id:el_id};
	$.ajax({
            dataType:"json",
            method:"POST",
            url:"Inicio",
            data:datos
	}).done(function(json){
            
            console.log("datos elimninados ",json);
            cargar_datos();
	}).fail(function(){

	}).always(function(){
            Swal.close();
	})
    }
    function cargar_datos(){
	mostrar_cargando("Procesando solicitud","Espere mientras se obtiene la información")
	var datos = {"consultar_datos":"si_consultalos"};
	$.ajax({
            dataType:"json",
            method:"POST",
            url:"Inicio",
            data:datos
	}).done(function(json){
            Swal.close();
            console.log("datos consultados ",json);
            //if (json[0]=="Exito") {
            $("#cargar_tabla").empty().html(json[0]['la_tabla_html']);
            $('#tabla_empleados').DataTable({
                responsive: true,
                pageLength:10,
                lengthMenu:[[10,20,30,-1],[10,20,30,'Todo']],
                "language":{
                    "sSearch":"Buscar:",
                    "oPaginate":{
                        "sFirst":"Primero",
                        "sLast":"Último",
                        "sNext":"Siguiente",
                        "sPrevious":"Anterior"
                    }
                }
            });
            //}
	}).fail(function(){

	}).always(function(){

	})

    }
    
    function mostrar_cargando(titulo,mensaje=""){
	Swal.fire({
	  title: titulo,
	  html: mensaje,
	  timer: 2000,
	  timerProgressBar: true,
	  didOpen: () => {
	    Swal.showLoading()
	     
	  },
	  willClose: () => {
	     
	  }
	}).then((result) => {
	  /* Read more about handling dismissals below */
	  if (result.dismiss === Swal.DismissReason.timer) {
	    console.log('I was closed by the timer')
	  }
	})
    }