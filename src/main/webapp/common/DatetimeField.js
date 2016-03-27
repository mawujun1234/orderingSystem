/** 
 * 带时间的日期输入控件 
 * 转载请注明来自于gogo1217.iteye.com 
 */  
Ext.define('y.common.DatetimeField', {
    extend: 'Ext.form.field.Date',
    alias: 'widget.datetimefield',
    requires: ['y.common.DatetimePicker'],

    //<locale>
    /**
     * @cfg {String} format
     * The default date format string which can be overriden for localization support. The format must be valid
     * according to {@link Ext.Date#parse}.
     */
    format: "Y-m-d H:i:s",
    //</locale>
    //<locale>
    /**
     * @cfg {String} altFormats
     * Multiple date formats separated by "|" to try when parsing a user input value and it does not match the defined
     * format.
     */
    format: "Y-m-d H:i:s",
    width: 270,

    mimicBlur: function(e) {
        var me = this,
            picker = me.picker;

        // ignore mousedown events within the picker element
        if (!picker || !e.within(picker.el, false, true) && !e.within(picker.timePicker.el, false, true)) {
            me.callParent(arguments);
        }
    },
    triggerBlur: function() {
        return false;
    },
    collapseIf: function(e) {
        var me = this,
            picker = me.picker;

        if ((Ext.getVersion().major == 4 && !me.isDestroyed &&
          !e.within(me.bodyEl, false, true) && !e.within(me.picker.el, false, true) &&
          !e.within(me.picker.timePicker.el, false, true)) ||
          (Ext.getVersion().major == 5 &&
          !Ext.fly(e.target).isFocusable() &&
          !me.isDestroyed &&
          !e.within(me.bodyEl, false, true) && !me.owns(e.target))
          && !e.within(picker.timePicker.el, false, true)) {
            me.collapse();
        }
    },

    createPicker: function() {
        var me = this,
            format = Ext.String.format;

        var picker =  new y.common.DatetimePicker({
            pickerField: me,
            floating: true,
            hidden: true,
            focusable: false, // Key events are listened from the input field which is never blurred
            focusOnShow: true,
            hideOnSelect: false,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: false,
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: {
                scope: me,
                select: me.onSelect
            },
            keyNavConfig: {
                esc: function() {
                    me.collapse();
                }
            }
        });
        return picker;
    },
    onSelect: function(m, d) {
        var me = this;

        me.setValue(d);
        me.fireEvent('select', me, d);
        //me.collapse();
    }
});