from django import forms


class frm_add_product(forms.Form):
    name = forms.CharField(required=True)
    expiration = forms.CharField(required=True)
    price = forms.FloatField(required=True)

    def is_valid(self):
        valid = True
        if not super(frm_add_product, self).is_valid():
            self.validation_add_error('Por favor, verifique os dados informados')
            valid = False
        return valid

    def validation_add_error(self, message):
        errors = self._errors.setdefault(forms.forms.NON_FIELD_ERRORS, forms.utils.ErrorList())
        errors.append(message)
