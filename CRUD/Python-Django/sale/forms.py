from django import forms


class frm_sell_products(forms.Form):
    date = forms.DateField(required=True)

    def is_valid(self):
        valid = True
        #if not super(frm_sell_products, self).is_valid():
          #  self.validation_add_error('Por favor, verifique os dados informados')
           # valid = False
        return valid

    def validation_add_error(self, message):
        errors = self._errors.setdefault(forms.forms.NON_FIELD_ERRORS, forms.utils.ErrorList())
        errors.append(message)
