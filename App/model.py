"""
 * Copyright 2020, Departamento de sistemas y Computación,
 * Universidad de Los Andes
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
 *
 * Contribuciones:
 *
 * Dario Correal - Version inicial
 """


import config as cf
import datetime
from DISClib.ADT import list as lt
from DISClib.ADT import stack as st
from DISClib.ADT import queue as qu
from DISClib.ADT import map as mp
from DISClib.ADT import minpq as mpq
from DISClib.ADT import indexminpq as impq
from DISClib.ADT import orderedmap as om
from DISClib.DataStructures import mapentry as me
from DISClib.Algorithms.Sorting import shellsort as sa
from DISClib.Algorithms.Sorting import insertionsort as ins
from DISClib.Algorithms.Sorting import selectionsort as se
from DISClib.Algorithms.Sorting import mergesort as merg
from DISClib.Algorithms.Sorting import quicksort as quk
assert cf

"""
Se define la estructura de un catálogo de videos. El catálogo tendrá
dos listas, una para los videos, otra para las categorias de los mismos.
"""

# Construccion de modelos


def new_data_structs():
    """
    Inicializa las estructuras de datos del modelo. Las crea de
    manera vacía para posteriormente almacenar la información.
    """
    #TODO: Inicializar las estructuras de datos
    data = {"accidentes": None,
            "fechaIndice": None,
#            "horaIndice": None,
            }
    data["accidentes"] = lt.newList("ARRAY_LIST", compare)
    data["fechaIndice"] = om.newMap(omaptype="RBT",
                                  comparefunction=compare)
    
    return data

# Funciones para agregar informacion al modelo

def add_data(data_structs, data):
    """
    Función para agregar nuevos elementos a la lista
    """
    #TODO: Crear la función para agregar elementos a una lista
    
    lt.addLast(data_structs["accidentes"], data)
    updatefecha(data_structs["fechaIndice"], data)
    
    return data_structs

def ultimos_y_primeros_carga(data_structs):
    
    data_structs = data_structs["accidentes"] 
    lista = []
    tamaño = lt.size(data_structs)
    
    primero = lt.firstElement(data_structs)
    segundo = lt.getElement(data_structs, 2)
    tercero = lt.getElement(data_structs,3)
    
    ultimo = lt.lastElement(data_structs)
    penultimo = lt.getElement(data_structs, (tamaño-1))
    antepenultimo = lt.getElement(data_structs, (tamaño-2))
    
    lista.append(primero)
    lista.append(segundo)
    lista.append(tercero)
    lista.append(antepenultimo)
    lista.append(penultimo)
    lista.append(ultimo)
    sublista = []
    
    for diccionario in lista:
        dicc={"CODIGO_ACCIDENTE":diccionario["CODIGO_ACCIDENTE"],
              "FECHA_HORA_ACC":diccionario["FECHA_HORA_ACC"],
              "LOCALIDAD":diccionario["LOCALIDAD"],
              "DIRECCION":diccionario["DIRECCION"],
              "GRAVEDAD":diccionario["GRAVEDAD"],
              "CLASE_ACC":diccionario["CLASE_ACC"],
              "LATITUD":diccionario["LATITUD"],
              "LONGITUD":diccionario["LONGITUD"]
              }
        sublista.append(dicc)
    
    return sublista
# Funciones para creacion de datos

def updatefecha(map, accidente):
    fecha = accidente["FECHA_OCURRENCIA_ACC"]
    fechaAccidente = datetime.datetime.strptime(fecha, "%Y/%m/%d")
    entry = om.get(map, fechaAccidente.date())
    if entry is None:
        fechaEntry = new_data_fecha(fechaAccidente.date(),accidente)
        om.put(map,fechaAccidente.date(), fechaEntry)
    else:
        isnone = entry["value"]["accidentes"]
        fechaEntry = lt.addLast(entry["value"]["accidentes"],accidente)
    
    
    return map    

def new_data_fecha(id, info):
    """
    Crea una nueva estructura para modelar los datos
    """
    #TODO: Crear la función para estructurar los datos
    
    entry = {"fecha": id,
             "accidentes": None
             }
    
    accdientes = lt.newList('ARRAY_LIST', compare)
    lt.addLast(accdientes, info)
    
    entry["accidentes"] = accdientes

    return entry

# Funciones de consulta

def get_data(data_structs, id):
    """
    Retorna un dato a partir de su ID
    """
    #TODO: Crear la función para obtener un dato de una lista
    pass


def data_size(data_structs):
    """
    Retorna el tamaño de la lista de datos
    """
    #TODO: Crear la función para obtener el tamaño de una lista
    pass


def req_1(data_structs):
    """
    Función que soluciona el requerimiento 1
    """
    # TODO: Realizar el requerimiento 1
    pass


def req_2(data_structs):
    """
    Función que soluciona el requerimiento 2
    """
    # TODO: Realizar el requerimiento 2
    pass


def req_3(data_structs):
    """
    Función que soluciona el requerimiento 3
    """
    # TODO: Realizar el requerimiento 3
    pass


def req_4(data_structs):
    """
    Función que soluciona el requerimiento 4
    """
    # TODO: Realizar el requerimiento 4
    pass


def req_5(data_structs):
    """
    Función que soluciona el requerimiento 5
    """
    # TODO: Realizar el requerimiento 5
    pass


def req_6(data_structs):
    """
    Función que soluciona el requerimiento 6
    """
    # TODO: Realizar el requerimiento 6
    pass


def req_7(data_structs):
    """
    Función que soluciona el requerimiento 7
    """
    # TODO: Realizar el requerimiento 7
    pass


def req_8(data_structs):
    """
    Función que soluciona el requerimiento 8
    """
    # TODO: Realizar el requerimiento 8
    pass


# Funciones utilizadas para comparar elementos dentro de una lista

def compare(data_1, data_2):
    """
    Función encargada de comparar dos datos
    """
    #TODO: Crear función comparadora de la lista
    if (data_1 == data_2):
        return 0
    elif (data_1 > data_2):
        return 1
    else:
        return -1

# Funciones de ordenamiento


def sort_criteria(data_1, data_2):
    """sortCriteria criterio de ordenamiento para las funciones de ordenamiento

    Args:
        data1 (_type_): _description_
        data2 (_type_): _description_

    Returns:
        _type_: _description_
    """
    #TODO: Crear función comparadora para ordenar
    pass


def sort(data_structs):
    """
    Función encargada de ordenar la lista con los datos
    """
    #TODO: Crear función de ordenamiento
    pass
