"""
 * Copyright 2020, Departamento de sistemas y Computación, Universidad
 * de Los Andes
 *
 *
 * Desarrolado para el curso ISIS1225 - Estructuras de Datos y Algoritmos
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along withthis program.  If not, see <http://www.gnu.org/licenses/>.
 """

import config as cf
import sys
import controller
from DISClib.ADT import list as lt
from DISClib.ADT import stack as st
from DISClib.ADT import queue as qu
from DISClib.ADT import map as mp
from DISClib.DataStructures import mapentry as me
from datetime import timedelta as td
import matplotlib.pyplot as plt
assert cf
import tabulate
import traceback
import csv
...
csv.field_size_limit(2147483647)

"""
La vista se encarga de la interacción con el usuario
Presenta el menu de opciones y por cada seleccion
se hace la solicitud al controlador para ejecutar la
operación solicitada
"""


def new_controller():
    """
        Se crea una instancia del controlador
    """
    #TODO: Llamar la función del controlador donde se crean las estructuras de datos
    
    control = controller.new_controller()
    
    return control

def print_menu():
    print("Bienvenido")
    print("1- Cargar información")
    print("2- Ejecutar Requerimiento 1")
    print("3- Ejecutar Requerimiento 2")
    print("4- Ejecutar Requerimiento 3")
    print("5- Ejecutar Requerimiento 4")
    print("6- Ejecutar Requerimiento 5")
    print("7- Ejecutar Requerimiento 6")
    print("8- Ejecutar Requerimiento 7")
    print("9- Ejecutar Requerimiento 8")
    print("0- Salir")

def print_tamaño_Datos():
    print("Elija el tamaño de los datos: ")
    print("1. Small")
    print("2. 5%")
    print("3. 10%")
    print("4. 20%")
    print("5. 30%")
    print("6. 50%")
    print("7. 80%")
    print("8. Large")


def load_data(control):
    """
    Carga los datos
    """
    #TODO: Realizar la carga de datos
    tamaño = tamaño_de_datos()
    
    data_structs, data = controller.load_data(control,"siniestros/datos_siniestralidad-"+tamaño+".csv")
    
    print('Datos cargados: ')
    header = data[0].keys()
    rows =  [x.values() for x in data]
    print(tabulate.tabulate(rows,header,tablefmt="grid",stralign="center",maxcolwidths= 15,maxheadercolwidths=15))

    
def tamaño_de_datos():
    print_tamaño_Datos()
    datos=int(input("Elija una opcion: "))
    if datos == 1:
        tamaño_datos="small"
    elif datos == 2:
        tamaño_datos="5pct"
    elif datos == 3:
        tamaño_datos="10pct"
    elif datos == 4:
        tamaño_datos="20pct"
    elif datos == 5:
        tamaño_datos="30pct"
    elif datos == 6:
        tamaño_datos="50pct"
    elif datos == 7:
        tamaño_datos="80pct"
    elif datos == 8:
        tamaño_datos="large"
    else:
        print("seleccion invalidad, oprima enter para continuar:")
    return tamaño_datos

def mes_a_num(mes):
    mes= mes.lower()
    if mes == "enero":
        mes=1
    elif mes == "febrero":
        mes=2
    elif mes=="marzo":
        mes=3
    elif mes == "abril":
        mes=4
    elif mes=="mayo":
        mes=5
    elif mes == "junio":
        mes=6
    elif mes=="julio":
        mes=7
    elif mes == "agosto":
        mes=8
    elif mes=="septiembre":
        mes=9
    elif mes == "octubre":
        mes=10
    elif mes=="noviembre":
        mes=11
    else: 
        mes= 12
    return mes


def print_data(control, id):
    """
        Función que imprime un dato dado su ID
    """
    #TODO: Realizar la función para imprimir un elemento
    pass

def print_req_1(control):
    """
        Función que imprime la solución del Requerimiento 1 en consola
    """
    # TODO: Imprimir el resultado del requerimiento 1
    pass


def print_req_2(control):
    """
        Función que imprime la solución del Requerimiento 2 en consola
    """
    # TODO: Imprimir el resultado del requerimiento 2
    pass


def print_req_3(control):
    """
        Función que imprime la solución del Requerimiento 3 en consola
    """
    # TODO: Imprimir el resultado del requerimiento 3
    pass


def print_req_4(control):
    """
        Función que imprime la solución del Requerimiento 4 en consola
    """
    # TODO: Imprimir el resultado del requerimiento 4
    pass


def print_req_5(control):
    """
        Función que imprime la solución del Requerimiento 5 en consola
    """
    # TODO: Imprimir el resultado del requerimiento 5
    pass


def print_req_6(control):
    """
        Función que imprime la solución del Requerimiento 6 en consola
    """
    # TODO: Imprimir el resultado del requerimiento 6
    pass


def print_req_7(control):
    """
        Función que imprime la solución del Requerimiento 7 en consola
    """
    anio= input("Ingrese el año: ")
    mes= input("Ingrese el mes: ")
    mes_2= mes_a_num(mes)
    lista, tabla= controller.req_7(control, anio, mes_2)
    
    for dia in lt.iterator(lista):
        fecha = lt.firstElement(dia)
        fecha = fecha["FECHA_OCURRENCIA_ACC"]
        print("Los accidentes del día " + str(fecha))
        lista=[]
        for dato in lt.iterator(dia):
            dicc={
            "CODIGO_ACCIDENTE": data["CODIGO_ACCIDENTE"],
            "DIA_OCURRENCIA_ACC": data["DIA_OCURRENCIA_ACC"],
            "Dirección": data["DIRECCION"],
            "Gravedad" : data["GRAVEDAD"],
            "Clase de accidente": data["CLASE_ACC"],
            "LOCALIDAD": data["LOCALIDAD"],
            "FECHA_HORA_ACC": data["FECHA_HORA_ACC"],
            "Latitud": data["LATITUD"],
            "Longitud": data["LONGITUD"]
            
            }
            lista.append(dicc)
            
        header = lista[0].keys()
        rows =  [x.values() for x in lista]
        print(tabulate.tabulate(rows,header,tablefmt="grid",maxcolwidths= 10,maxheadercolwidths=6))
        
    size_horas= contar_horas(tabla)
    dicc={}
    for i in range(24):
        time= td(hours=i)
        dicc[str(time)]=0
    keys= mp.keySet(tabla)
    for key in keys:
        valor=mp.get(tabla, key)
        valor= me.getValue(valor)
        time= td(hours=key)
        dicc[str(time)]= valor
        
    names = list(dicc.keys())
    values = list(dicc.values())
    plt.figure(figsize=(10,10))
    plt.bar(range(len(dicc)), values, tick_label=names)       

    
    plt.title('Frecuencia de '+ str(size_horas)+ " accidentes por hora del día "+ " para el mes de "+ str(mes)+ " de "+ str(anio))
    plt.xlabel('Hora del día')
    plt.xticks(fontsize=7, rotation=90)
    plt.ylabel('Número de accidentes')
    plt.show()
    
def contar_horas(tabla):
    values=mp.valueSet(tabla)
    size=0
    for dato in lt.iterator(values):
        size+=dato
        
    return size
    


def print_req_8(control):
    """
        Función que imprime la solución del Requerimiento 8 en consola
    """
    # TODO: Imprimir el resultado del requerimiento 8
    pass


# Se crea el controlador asociado a la vista
control = new_controller()

# main del reto
if __name__ == "__main__":
    """
    Menu principal
    """
    working = True
    #ciclo del menu
    while working:
        print_menu()
        inputs = input('Seleccione una opción para continuar\n')
        try:
            if int(inputs) == 1:
                print("Cargando información de los archivos ....\n")
                data = load_data(control)
            elif int(inputs) == 2:
                print_req_1(control)

            elif int(inputs) == 3:
                print_req_2(control)

            elif int(inputs) == 4:
                print_req_3(control)

            elif int(inputs) == 5:
                print_req_4(control)

            elif int(inputs) == 6:
                print_req_5(control)

            elif int(inputs) == 7:
                print_req_6(control)

            elif int(inputs) == 8:
                print_req_7(control)

            elif int(inputs) == 9:
                print_req_8(control)

            elif int(inputs) == 0:
                working = False
                print("\nGracias por utilizar el programa")
                
            else:
                print("Opción errónea, vuelva a elegir.\n")
        except Exception as exp:
            print("ERR:", exp)
            traceback.print_exc()
    sys.exit(0)
