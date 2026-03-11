async function fetchWithAuth(url, options = {}) {
  let token = localStorage.getItem("accessToken")
  options.headers = {
    ...options.headers,
    Authorization: "Bearer " + token
  }
  let res = await fetch(url, options)
  if (res.status === 401) {
    const refreshToken = localStorage.getItem("refreshToken")
    const refreshRes = await fetch("/api/auth/refresh", {
      method: "POST",
      headers: {
        Authorization: "Bearer " + refreshToken
      }
    })
    if (!refreshRes.ok) {
      window.location = "/signin"
      return
    }
    const data = await refreshRes.json()
    localStorage.setItem("accessToken", data.access_token)
    localStorage.setItem("refreshToken", data.refresh_token)
    options.headers.Authorization = "Bearer " + data.access_token
    res = await fetch(url, options)
  }

  return res
}
