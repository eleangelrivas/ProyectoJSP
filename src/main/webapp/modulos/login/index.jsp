<%-- 
    Document   : index
    Created on : 01-nov-2021, 13:36:24
    Author     : elenilsonangel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ include file="../../inc/template_start.jsp" %> 

<!-- Login Background -->
<div id="login-background">
    <!-- For best results use an image with a resolution of 2560x400 pixels (prefer a blurred image for smaller file size) -->
    <img src="../../img/placeholders/headers/login_header.jpg" alt="Login Background" class="animation-pulseSlow">
</div>
<!-- END Login Background -->

<!-- Login Container -->
<div id="login-container" class="animation-fadeIn">
    <!-- Login Title -->
    <div class="login-title text-center">
        <h1><small>Por favor inicie sesión</small></h1>
    </div>
    <!-- END Login Title -->

    <!-- Login Block -->
    <div class="block push-bit">
        <!-- Login Form -->
        <form id="formulario_login" method="post" id="form-login" class="form-horizontal form-bordered form-control-borderless">
            <input type="hidden" name="filtro_accion" value="si_loguear">
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="gi gi-envelope"></i></span>
                        <input type="text" id="login-email" autocomplete="off" required name="login-email" class="form-control input-lg" placeholder="Email">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="gi gi-asterisk"></i></span>
                        <input type="password" id="login-password" autocomplete="off" required name="login-password" class="form-control input-lg" placeholder="Password">
                    </div>
                </div>
            </div>
            <div class="form-group form-actions">
                <div class="col-xs-4">
                     
                </div>
                <div class="col-xs-8 text-right">
                    <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-angle-right"></i> Ingresar al sistema</button>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12 text-center">
                     
                </div>
            </div>
        </form>
        <!-- END Login Form -->

      
    </div>
    <!-- END Login Block -->

     
</div>
<!-- END Login Container -->
 

<%@ include file="../../inc/template_scripts.jsp" %> 
<script>
    $(function(){
        console.log("El jquery en login funcionando");


        $(document).on("submit","#formulario_login",function(e){

            e.preventDefault();
            var datos = $("#formulario_login").serialize();
            $.ajax({
                dataType: "json",
                method: "POST",
                url:'Login',
                data : datos,
            }).done(function(json) {
                    console.log("EL TRAER al logueo",json);
                    if(json.length==0){
                        Swal.fire(
                            'Ooops',
                            'Los datos no existen',
                            'error'
                          )
                    }else if(json[0]['resultado']=="Exito"){
                        Swal.fire(
                            'Excelente',
                            'Bienvenido al sistema '+json[0]['first_name'],
                            'success'
                          );
                          
                          var timer = setInterval(function(){
                              $(location).attr("href","Inicio");
                              clearTimeout(timer);
                          },3500)
                    }else{
                        Swal.fire(
                            'Oops',
                            'Contraseña incorrecta',
                            'info'
                          )
                    }
                    /*if(json[0]['resultado']=="Exito"){
                        $(location).attr('href','Inicio');
                    }*/
                    

            }).fail(function(){
 
            }).always(function(){
                
            });

        });
    })
</script>

<%@ include file="../../inc/template_end.jsp" %>

