# -*- coding: utf-8 -*-
from datetime import date

from dateutil.relativedelta import *

from odoo import models, fields, api


class TipoProducto(models.Model):
    _name = 'cestacompra.tipo_producto'
    _description = 'cestacompra.tipo_producto'

    name = fields.Char()
    precio = fields.Float("Precio")

    productos_id = fields.One2many('cestacompra.producto', 'tipo_producto_id')


#     @api.depends('value')
#     def _value_pc(self):
#         for record in self:
#             record.value2 = float(record.value) / 100

class Producto(models.Model):
    _name = 'cestacompra.producto'
    _description = 'cestacompra.producto'

    name = fields.Char("Nombre")
    cantidad = fields.Integer("Cantidad")
    suma_precio = fields.Float("Total de producto", compute="_total_producto")

    tipo_producto_id = fields.Many2one('cestacompra.tipo_producto')
    compra_id = fields.Many2one("cestacompra.compra")

    # @api.depends('tipo_producto_id')
    # def _nombre(self):
    #    for record in self:
    #        record.name = record.tipo_producto_id.name

    @api.depends('tipo_producto_id', 'cantidad')
    def _total_producto(self):
        for record in self:
            record.suma_precio = record.tipo_producto_id.precio * record.cantidad


class Compra(models.Model):
    _name = 'cestacompra.compra'
    _description = 'cestacompra.compra'

    fecha = fields.Date()
    precio_total = fields.Float("Precio total", compute="_total")

    productos_compra_id = fields.One2many("cestacompra.producto", "compra_id")
    cliente_id = fields.Many2one("cestacompra.cliente")
    establecimiento_id = fields.Many2one("cestacompra.establecimiento")

    @api.depends('productos_compra_id')
    def _total(self):
        for record in self:
            suma = 0
            for p in record.productos_compra_id:
                suma += p.suma_precio
            record.precio_total = suma


class Cliente(models.Model):
    _name = "cestacompra.cliente"
    _description = "cestacompra.cliente"

    name = fields.Char("Nombre")
    apellidos = fields.Char("Apellidos")
    direccion = fields.Char("Direccion")
    fecha_nacimiento = fields.Date("Fecha de nacimiento")
    edad = fields.Integer(string="Edad", compute='_age_compute')

    compras_id = fields.One2many("cestacompra.compra", "cliente_id")

    @api.depends('fecha_nacimiento')
    def _age_compute(self):
        today = date.today()
        for record in self:
            record.edad = relativedelta(today, record.fecha_nacimiento).years


class Establecimiento(models.Model):
    _name = "cestacompra.establecimiento"
    _description = "cestacompra.establecimiento"

    name = fields.Char("Nombre")
    direccion = fields.Char("Direccion")
    presupuesto = fields.Float("Presupuesto")

    propietario_id = fields.Many2one("cestacompra.empleado")
    empleados_id = fields.One2many("cestacompra.empleado", "establecimiento_id")


class Empleado(models.Model):
    _name = "cestacompra.empleado"
    _description = "cestacompra.empleado"

    name = fields.Char("Nombre")
    apellidos = fields.Char("Apellidos")
    direccion = fields.Char("Direccion")
    fecha_nacimiento = fields.Date("Fecha de nacimiento")
    edad = fields.Integer(string="Edad", compute='_age_compute')
    posicion = fields.Char("Posición")
    sueldo = fields.Float("Sueldo")

    establecimiento_id = fields.Many2one("cestacomprea.establecimiento", "Empleado en")
    propietario_establecimientos_id = fields.One2many("cestacompra.establecimiento", "propietario_id")

    @api.depends('fecha_nacimiento')
    def _age_compute(self):
        today = date.today()
        for record in self:
            record.edad = relativedelta(today, record.fecha_nacimiento).years