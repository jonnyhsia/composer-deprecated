package com.jonnyhsia.composer.app

import me.drakeet.multitype.Items

class Models : Items {

    constructor() : super()

    constructor(initialCapacity: Int) : super(initialCapacity)

    constructor(c: Collection<*>) : super(c)

}
