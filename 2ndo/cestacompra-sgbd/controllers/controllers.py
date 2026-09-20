# -*- coding: utf-8 -*-
# from odoo import http


# class Cestacompra(http.Controller):
#     @http.route('/cestacompra/cestacompra/', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/cestacompra/cestacompra/objects/', auth='public')
#     def list(self, **kw):
#         return http.request.render('cestacompra.listing', {
#             'root': '/cestacompra/cestacompra',
#             'objects': http.request.env['cestacompra.cestacompra'].search([]),
#         })

#     @http.route('/cestacompra/cestacompra/objects/<model("cestacompra.cestacompra"):obj>/', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('cestacompra.object', {
#             'object': obj
#         })
