/*
* 测试方法 动态获取内网ip
* */

export const getip = async () => {
    return new Promise((resolve) => {
        let ip_addr = '127.0.0.1'
        const RTCPeerConnection = window.RTCPeerConnection || window.webkitRTCPeerConnection || window.mozRTCPeerConnection;
        if (RTCPeerConnection) {
            const rtc = new RTCPeerConnection()
            rtc.createDataChannel(''); //创建一个可以发送任意数据的数据通道
            rtc.createOffer(offerDesc => { //创建并存储一个sdp数据
                rtc.setLocalDescription(offerDesc)
            }, e => {
                console.log(e)
            })
            rtc.onicecandidate = (evt) => { //监听candidate事件
                if (evt.candidate) {
                    ip_addr = evt.candidate.address
                }
            }
            setTimeout(()=>{
                resolve(ip_addr)
            },500)
        }
    })
}
