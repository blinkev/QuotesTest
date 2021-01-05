package com.example.quotes.app.all.list

import com.example.quotes.utils.FormattedString
import com.example.quotes.domain.Quote

data class AllQuotesListItem(val name: FormattedString, var isChecked: Boolean, val quote: Quote)