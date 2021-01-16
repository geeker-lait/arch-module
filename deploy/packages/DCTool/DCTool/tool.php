<?php
include_once('class.DBPDO.php');
define('DATABASE_NAME', 'unichain_account');

define('DATABASE_USER', 'root');

define('DATABASE_PASS', 'zfc1cfkyaghhvzrlcg*');

define('DATABASE_HOST', '106.12.71.3');

$DB = new DBPDO();

if( isset($_POST["qdh"])){
    $v =  "'".$_POST["qdh"]."'";

    $countiess = $DB->fetchAll("SELECT * FROM `unichain_account`.`account` WHERE yuan_source = $v ");

    $counties = array();
    foreach ($countiess as $key => $value) {
        array_push($counties,$value["account_name"]);
    }




}


if (isset($_GET['jiance'])) {
    $counties = unserialize($_POST['xlh']);

}



?>

    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
    <form action="/dctool/tool.php" method="post">
        渠道号:<input type="text" name="qdh" value="">
        <br>
        <br>
        <input type="submit" value="Submit">
    </form>

    <form action="/dctool/tool.php?jiance=jiance" method="post">
        <input hidden="hidden"  type="text" name="xlh" value=<?php
        if (isset($counties)) {
            $xlh = serialize($counties);
            echo "$xlh";
        }
        ?> >
        <input type="submit" value="查看风险">
    </form>


    <?php
    $mYerror_counties = array();
    if (!empty($counties)) {
        foreach ($counties as $key => $value){ ?>
            <a > <?php printf($value);?> </a>  <a href="">
                <?php
                if (isset($_GET['jiance'])) {
                    if (testTel($value)) {
                        echo "ok";
                    } else {
                        echo "失败";
                        array_push($mYerror_counties,$value);
                    }
                }
                ?>
            </a> <br>
        <?php }} ?>    


     <hr>
     <h1>失败的电话号码</h1>
     <hr>
    <?php
    if (!empty($mYerror_counties)) {
        foreach ($mYerror_counties as $key => $value){
            echo $value."<br>";
        }
    }
    ?>
     

    </body>
    </html>

<?php

function testTel($tel) {
    //初始化
    $curl = curl_init();
//设置抓取的url
    curl_setopt($curl, CURLOPT_URL, 'https://api.253.com/open/unn/batch-ucheck');
//设置头文件的信息作为数据流输出
    curl_setopt($curl, CURLOPT_HEADER, 1);
//设置获取的信息以文件流的形式返回，而不是直接输出。
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
//设置post方式提交
    curl_setopt($curl, CURLOPT_POST, 1);

    curl_setopt($curl, CURLOPT_NOBODY, FALSE); //表示需要response body
//设置post数据
    $post_data = array(
        "appId" => "0CtcBj1m",
        "appKey" => "VpHrSodW",
        "mobiles"=> $tel,
    );
    curl_setopt($curl, CURLOPT_POSTFIELDS, $post_data);
//执行命令
    $data = curl_exec($curl);

    ;



    if (!empty($data)) {

        if (curl_getinfo($curl, CURLINFO_HTTP_CODE) == '200') {
            $headerSize = curl_getinfo($curl, CURLINFO_HEADER_SIZE);
            $header = substr($data, 0, $headerSize);
            $body = substr($data, $headerSize);

            if(strpos($body ,'"status":"1"}],"code"')!==false){
                return true;
            }else{
                return false;
            }

        }
    }
//关闭URL请求
    curl_close($curl);

//显示获得的数据
}

?>