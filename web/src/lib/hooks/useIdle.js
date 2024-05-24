/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

import { useEffect, useState } from 'react'
import { throttle } from 'throttle-debounce'
import { off, on } from '@/lib/utils'

const defaultEvents = ['mousemove', 'mousedown', 'resize', 'keydown', 'touchstart', 'wheel']
const oneMinute = 60e3

const useIdle = (ms = oneMinute, initialState = false, events = defaultEvents) => {
  const [state, setState] = useState(initialState)

  useEffect(() => {
    let mounted = true
    let timeout
    let localState = state

    const set = newState => {
      if (mounted) {
        localState = newState
        setState(newState)
      }
    }

    const onEvent = throttle(50, () => {
      if (localState) {
        set(false)
      } else {
        localStorage.setItem('lastActiveTime', Date.now())
      }

      clearTimeout(timeout)
      timeout = setTimeout(() => set(true), ms)
    })

    const onVisibility = () => {
      if (!document.hidden) {
        onEvent()
      }
    }

    for (let i = 0; i < events.length; i++) {
      on(window, events[i], onEvent)
    }
    on(document, 'visibilitychange', onVisibility)

    timeout = setTimeout(() => set(true), ms)

    return () => {
      mounted = false

      for (let i = 0; i < events.length; i++) {
        off(window, events[i], onEvent)
      }
      off(document, 'visibilitychange', onVisibility)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [ms, events])

  return state
}

export default useIdle
