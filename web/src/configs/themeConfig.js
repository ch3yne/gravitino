/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

// @ts-check

/**
 * @typedef {import('react-hot-toast').ToastPosition} ToastPosition
 */

const themeConfig = {
  /**
   * @description set the default theme mode to light or dark
   * @type {'light'|'dark'}
   */
  mode: 'dark',

  /**
   * @description message box position
   * @type {ToastPosition}
   */
  toastPosition: 'top-right',

  /**
   * @description message box duration in milliseconds
   * @type {number}
   */
  toastDuration: 4000
}

export default themeConfig
